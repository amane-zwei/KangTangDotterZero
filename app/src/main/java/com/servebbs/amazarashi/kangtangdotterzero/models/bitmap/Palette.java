package com.servebbs.amazarashi.kangtangdotterzero.models.bitmap;

public class Palette {
    private int index = 0;
//        public void setNewColor(int color){
//            int idx;
//            for( idx=0; idx<array.size(); idx++ ){
//                if( color == array.elementAt(idx)){
//                    index = idx;
//                    return;
//                }
//            }
//            if( canAddColor() ){
//                array.add(color);
//                index = idx;
//                return;
//            }
//            array.setElementAt(color, index);
//        }
//
//        public void addColorIndex(int delta){
//            setIndex(this.index+delta);
//        }
//        public void setIndex(int index){
//            int length = array.size();
//            index = ((index % length) + length) % length;
//
//            this.index = index;
//        }
//
//        public boolean canAddColor(){ return array.size() < colorMax; }
//
//        public int getIndex(){ return this.index; }
//        public int getIndex(int color){
//            int length = array.size();
//            for(int idx=0; idx<length; idx++ ){
//                if( color == array.elementAt(idx) )
//                    return idx;
//            }
//            return 0;
//        }
//        public int forceGetIndex(int color){
//            int idx;
//            int length = array.size();
//            for(idx=0; idx<length; idx++ ){
//                if( color == array.elementAt(idx) )
//                    return idx;
//            }
//            if( canAddColor() ){
//                array.add(color);
//                return idx;
//            }
//            return 0;
//        }
//
//        public int getColor(){ return getColor(this.index); }
//        public int getColor(int index){
//            int size = array.size();
//
//            if( size>0 )
//                return array.elementAt(((index%size)+size)%size);
//            return 0;
//        }
//    }
//    public boolean save(OutputStream os){
//        try{
//            PaletteHeader head = new PaletteHeader();
//            head.paletteSize = palette.size();
//            head.colorSize = getArraySize();
//            head.paletteIndex = paletteIndex;
//            head.save(os);
//
//            byte[] buff = new byte[4 * head.colorSize];
//            ByteBuffer bb = ByteBuffer.wrap(buff);
//            for( int p=0; p<palette.size(); p++ ){
//                ColorArray array = palette.elementAt(p);
//                for( int idx=0; idx<array.size(); idx++ ){
//                    bb.asIntBuffer().put(idx, array.getColor(idx));
//                }
//                os.write(buff);
//            }
//
//        } catch( IOException e){
//            return false;
//        }
//        return true;
//    }
//
//    public Palette load(InputStream is){
//        clear();
//        ByteBuffer bb;
//        try {
//            PaletteHeader head = new PaletteHeader();
//            head.load(is);
//
//            paletteIndex = head.paletteIndex;
//
///*			byte[] buff = new byte[4 * head.colorSize];
//			for( int idx=0; idx<head.paletteSize; idx++ ){
//				is.read(buff);
//				bb = ByteBuffer.wrap(buff);
//				palette.add(new ColorArray(bb.asIntBuffer().array()));
//			}
//*/			byte[] buff = new byte[4];
//            for( int idx=0; idx<head.paletteSize; idx++ ){
//                ColorArray array = new ColorArray();
//
//                for( int color=0; color<head.colorSize; color++ ){
//                    is.read(buff);
//                    bb = ByteBuffer.wrap(buff);
//                    array.addColor(bb.getInt());
//                }
//                palette.add(array);
//            }
//        } catch (Exception e) {
//            return null;
//        }
//        return null;
//    }
}